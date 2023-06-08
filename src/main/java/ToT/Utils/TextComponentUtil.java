package ToT.Utils;

import net.md_5.bungee.api.chat.BaseComponent;

public class TextComponentUtil {
    public static BaseComponent[] append(BaseComponent[] baseComponents, BaseComponent... components) {
        BaseComponent[] newComponents = new BaseComponent[baseComponents.length + components.length];
        System.arraycopy(baseComponents, 0, newComponents, 0, baseComponents.length);
        System.arraycopy(components, 0, newComponents, baseComponents.length, components.length);
        return newComponents;
    }
}
