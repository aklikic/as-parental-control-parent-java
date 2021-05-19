package com.lightbend.gsa.parentalcontrol.parent;

import com.akkaserverless.javasdk.Reply;
import com.akkaserverless.javasdk.ServiceCallRef;
import com.akkaserverless.javasdk.action.Action;
import com.akkaserverless.javasdk.action.ActionContext;
import com.akkaserverless.javasdk.action.Handler;
import com.google.protobuf.Empty;
import com.lightbend.gsa.parentalcontrol.childdevice.persistence.PcChilddeviceDomain;

@Action
public class ChildDeviceSubscribeAction {

    private static final String PARENT_SERVICE = "com.lightbend.gsa.parentalcontrol.parent.ParentService";

    @Handler
    public Reply<Empty> consumeParentAdded(PcChilddeviceDomain.ParentAdded message, ActionContext ctx) {
        ServiceCallRef<PcParentApi.AddChildDeviceCommand> ref =
                ctx.serviceCallFactory()
                        .lookup(
                                PARENT_SERVICE,
                                "AddChildDevice",
                                PcParentApi.AddChildDeviceCommand.class
                        );
        PcParentApi.AddChildDeviceCommand cmd = PcParentApi.AddChildDeviceCommand.newBuilder()
                .setParentId(message.getParentId())
                .setDeviceId(message.getDeviceId())
                .build();
        return Reply.forward(ref.createCall(cmd));
    }
    @Handler
    public Reply<Empty> consumeParentRemoved(PcChilddeviceDomain.ParentRemoved message, ActionContext ctx) {
        ServiceCallRef<PcParentApi.RemoveChildDeviceCommand> ref =
                ctx.serviceCallFactory()
                        .lookup(
                                PARENT_SERVICE,
                                "RemoveChildDevice",
                                PcParentApi.RemoveChildDeviceCommand.class
                        );
        PcParentApi.RemoveChildDeviceCommand cmd = PcParentApi.RemoveChildDeviceCommand.newBuilder()
                .setParentId(message.getParentId())
                .setDeviceId(message.getDeviceId())
                .build();
        return Reply.forward(ref.createCall(cmd));
    }
}
