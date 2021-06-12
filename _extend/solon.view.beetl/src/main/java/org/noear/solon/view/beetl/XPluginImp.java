package org.noear.solon.view.beetl;

import org.beetl.core.tag.Tag;
import org.noear.solon.SolonApp;
import org.noear.solon.core.Aop;
import org.noear.solon.core.Plugin;
import org.noear.solon.core.handle.RenderManager;

public class XPluginImp implements Plugin {
    public static boolean output_meta = false;

    @Override
    public void start(SolonApp app) {
        output_meta = app.cfg().getInt("solon.output.meta", 0) > 0;

        BeetlRender render =  BeetlRender.global();

        Aop.beanOnloaded(() -> {
            Aop.beanForeach((k, v) -> {
                if (k.startsWith("view:")) { //java view widget
                    if(Tag.class.isAssignableFrom(v.clz())) {
                        render.registerTag(k.split(":")[1], v.clz());
                    }
                    return;
                }

                if(k.startsWith("share:")){ //java share object
                    render.setSharedVariable(k.split(":")[1], v.raw());
                    return;
                }
            });
        });

        RenderManager.register(render);
        RenderManager.mapping(".htm",render);
        RenderManager.mapping(".btl",render);

//        if (Utils.loadClass("org.noear.solon.extend.auth.AuthUtil") != null) {
//            app.beanMake(HasPermissionTag.class);
//            app.beanMake(HasRoleTag.class);
//        }
    }
}
