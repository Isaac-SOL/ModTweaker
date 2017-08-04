package com.blamejared.compat.botania.lexicon.commands;

import minetweaker.MineTweakerAPI;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.api.player.IPlayer;
import minetweaker.api.server.ICommandFunction;
import vazkii.botania.api.BotaniaAPI;

import java.util.Set;

public class LexiconKnowledgeTypesLogger implements ICommandFunction{

    @Override
    public void execute(String[] arguments, IPlayer player) {
    	Set<String> types=BotaniaAPI.knowledgeTypes.keySet();
        System.out.println("Knowledge Types: " + types.size());
        for (String key : types) {
            System.out.println("Knowledge Type " + key);
            MineTweakerAPI.logCommand(key);
        }

        if (player != null) {
            player.sendChat(MineTweakerImplementationAPI.platform.getMessage("List generated; see minetweaker.log in your minecraft dir"));
        }
    }
}
