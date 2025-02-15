package de.erdbeerbaerlp.dcintegration.fabric.mixin;

import de.erdbeerbaerlp.dcintegration.fabric.util.CompatibilityUtils;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class MixinConfig implements IMixinConfigPlugin {

    private final HashMap<String, Boolean> conditionalMixins = new HashMap<>();

    @Override
    public void onLoad(String mixinPackage) {
        boolean styledChatLoaded = CompatibilityUtils.styledChatLoaded();
        conditionalMixins.put("de.erdbeerbaerlp.dcintegration.fabric.mixin.MixinNetworkHandler", !styledChatLoaded);
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return this.conditionalMixins.getOrDefault(mixinClassName, true);
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
