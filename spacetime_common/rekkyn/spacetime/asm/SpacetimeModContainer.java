package rekkyn.spacetime.asm;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

public class SpacetimeModContainer extends DummyModContainer {
        public SpacetimeModContainer() {
                super(new ModMetadata());
                ModMetadata myMeta = super.getMetadata();
                myMeta.authorList = Arrays.asList(new String[] { "Rekkyn" });
                myMeta.modId = "SpacetimeCore";
                myMeta.name = "Spacetime Core";
                myMeta.url = "github.com/Rekkyn/spacetimemod";
        }
        
        public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
        }
}
