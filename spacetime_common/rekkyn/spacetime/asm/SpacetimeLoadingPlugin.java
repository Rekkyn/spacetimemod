package rekkyn.spacetime.asm;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@TransformerExclusions({"rekkyn.spacetime.asm"})
public class SpacetimeLoadingPlugin implements IFMLLoadingPlugin {
    
    @Override
    public String[] getLibraryRequestClass() {
        return null;
    }
    
    @Override
    public String[] getASMTransformerClass() {
        return new String[] {"rekkyn.spacetime.asm.SpacetimeAccessTransformer"};
    }
    
    @Override
    public String getModContainerClass() {
        return "rekkyn.spacetime.SpacetimeModContainer";
    }
    
    @Override
    public String getSetupClass() {
        return null;
    }
    
    @Override
    public void injectData(Map<String, Object> data) {
    }
    
}
