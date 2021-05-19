package com.lightbend.gsa.parentalcontrol.parent;

import com.akkaserverless.javasdk.view.UpdateHandler;
import com.akkaserverless.javasdk.view.View;
import com.lightbend.gsa.parentalcontrol.parent.persistence.PcParentDomain;
import com.lightbend.gsa.parentalcontrol.parent.view.PcParentByNameView;

import java.util.Optional;

@View
public class ParentByNameView {

    @UpdateHandler
    public PcParentByNameView.ParentView processParentCreated(PcParentDomain.ParentCreated event, Optional<PcParentByNameView.ParentView> state){
        return state.orElse(PcParentByNameView.ParentView.newBuilder()
                                    .setParentId(event.getParentId())
                                    .setName(event.getName()).build()
        );
    }

    @UpdateHandler
    public PcParentByNameView.ParentView processParentUpdated(PcParentDomain.ParentUpdated event, Optional<PcParentByNameView.ParentView> state){
        return state.orElse(PcParentByNameView.ParentView.newBuilder()
                .setParentId(event.getParentId())
                .setName(event.getName()).build()
        );
    }

}
