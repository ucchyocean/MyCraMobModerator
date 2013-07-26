/*
 * @author     ucchy
 * @license    LGPLv3
 * @copyright  Copyright ucchy 2013
 */
package com.github.ucchyocean.mmdm;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * MOBのスポーンやドロップの調整を行う、独自プラグイン
 * @author ucchy
 */
public class MyCraMobModerator extends JavaPlugin implements Listener {

    /**
     * プラグインが有効になったときに呼び出されるメソッド
     * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
     */
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    /**
     * MOBがスポーンしたときに呼び出されるメソッド
     * @param event
     */
    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        
        // -- 通常世界に来たピッグゾンビをZAPする。
        // NOTE: ピッグゾンビが、スポーンエッグでスポーンし、かつ、
        //       スポーン場所にポータルがあるなら、ZAPする。
        Block block = event.getLocation().getBlock();
        if ( event.getEntityType() == EntityType.PIG_ZOMBIE &&
                event.getSpawnReason() == SpawnReason.SPAWNER_EGG &&
                block != null && block.getType() == Material.PORTAL ) {
            event.setCancelled(true);
            //getLogger().finest(">>> ZAP!! <<<");
        }
    }
    
    /**
     * Entityが死亡したときに呼び出されるメソッド
     * @param event 
     */
    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent event) {
        
        // -- アイアンゴーレムが、何もドロップしないようにする。
        if ( event.getEntityType() == EntityType.IRON_GOLEM ) {
            List<ItemStack> items = event.getDrops();
            //for ( ItemStack i : items ) {
            //    System.out.println("-- " + i.getType().toString());
            //}
            for ( int i=items.size()-1; i>=0; i-- ) {
                items.remove(i);
            }
        }
    }
}
