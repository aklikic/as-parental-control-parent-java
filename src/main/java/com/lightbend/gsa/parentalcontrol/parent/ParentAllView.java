package com.lightbend.gsa.parentalcontrol.parent;

import com.akkaserverless.javasdk.view.UpdateHandler;
import com.akkaserverless.javasdk.view.View;
import com.lightbend.gsa.parentalcontrol.parent.persistence.PcParentDomain;
import com.lightbend.gsa.parentalcontrol.parent.view.PcParentAllView;
import com.lightbend.gsa.parentalcontrol.parent.view.PcParentByNameView;

import java.util.Optional;

@View
public class ParentAllView {

    @UpdateHandler
    public PcParentAllView.ParentAllView processParentCreated(PcParentDomain.ParentCreated event, Optional<PcParentAllView.ParentAllView> state){
        return state.orElse(PcParentAllView.ParentAllView.newBuilder()
                                    .setParentId(event.getParentId())
                                    .setName(event.getName()).build()
        );
    }

    @UpdateHandler
    public PcParentAllView.ParentAllView processParentUpdated(PcParentDomain.ParentUpdated event, Optional<PcParentAllView.ParentAllView> state){
        return state.orElse(PcParentAllView.ParentAllView.newBuilder()
                .setParentId(event.getParentId())
                .setName(event.getName()).build()
        );
    }

}
