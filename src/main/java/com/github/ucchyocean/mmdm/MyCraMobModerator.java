/*
 * @author     ucchy
 * @license    LGPLv3
 * @copyright  Copyright ucchy 2013
 */
package com.github.ucchyocean.mmdm;

import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPortalEvent;
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
        
        // -- 通常世界に来たMOBをZAPする。
        if ( event.getSpawnReason() == SpawnReason.NETHER_PORTAL ) {
            event.setCancelled(true);
        }
    }
    
    /**
     * Entityがポータルを通過しようとしたときに呼び出されるメソッド
     * @param event 
     */
    @EventHandler
    public void onPortal(EntityPortalEvent event) {
        
        // -- MOBのポータル通過はすべて禁止する。
        event.setCancelled(true);
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
            for ( int i=items.size()-1; i>=0; i-- ) {
                items.remove(i);
            }
        }
    }
}
