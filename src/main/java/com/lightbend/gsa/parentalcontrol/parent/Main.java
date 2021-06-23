package com.lightbend.gsa.parentalcontrol.parent;

import com.akkaserverless.javasdk.AkkaServerless;
import com.lightbend.gsa.parentalcontrol.childdevice.persistence.PcChilddeviceDomain;
import com.lightbend.gsa.parentalcontrol.parent.persistence.ParentEntityImpl;
import com.lightbend.gsa.parentalcontrol.parent.persistence.PcParentDomain;
import com.lightbend.gsa.parentalcontrol.parent.subscribe.PcChilddeviceSubscribe;
import com.lightbend.gsa.parentalcontrol.parent.view.PcParentAllView;
import com.lightbend.gsa.parentalcontrol.parent.view.PcParentByNameView;
import com.lightbend.gsa.parentalcontrol.parent.PcParentApi;
public final class Main {
    
    public static void main(String[] args) throws Exception {
        new AkkaServerless()
            .registerEventSourcedEntity(
                ParentEntityImpl.class,
                PcParentApi.getDescriptor().findServiceByName("ParentService"),
                PcParentDomain.getDescriptor()
            )
            .registerView(
                ParentByNameView.class,
                PcParentByNameView.getDescriptor().findServiceByName("ParentByName"),
                "parent",
                PcParentDomain.getDescriptor(),
                PcParentByNameView.getDescriptor()
            )
            .registerView(
                    ParentAllView.class,
                    PcParentAllView.getDescriptor().findServiceByName("ParentAll"),
                    "parentAll",
                    PcParentDomain.getDescriptor(),
                    PcParentAllView.getDescriptor()
            )
            .registerAction(
                ChildDeviceSubscribeAction.class,
                PcChilddeviceSubscribe.getDescriptor().findServiceByName("ChildDeviceSubscribe"),
                PcChilddeviceDomain.getDescriptor()
            )
            .start().toCompletableFuture().get();
    }
    
}