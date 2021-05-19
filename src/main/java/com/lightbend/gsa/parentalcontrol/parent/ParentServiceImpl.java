package com.lightbend.gsa.parentalcontrol.parent;

import com.akkaserverless.javasdk.EntityId;
import com.akkaserverless.javasdk.eventsourcedentity.*;
import com.google.protobuf.Empty;
import com.lightbend.gsa.parentalcontrol.parent.persistence.PcParentDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/** An event sourced entity. */
@EventSourcedEntity(entityType = "ParentEntity")
public class ParentServiceImpl extends ParentService {
    private static Logger log = LoggerFactory.getLogger(ParentServiceImpl.class);
    @SuppressWarnings("unused")
    private final String entityId;
    private PcParentApi.Parent state;
    
    public ParentServiceImpl(@EntityId String entityId) {
        this.entityId = entityId;
    }
    
    @Override
    public PcParentDomain.ParentState snapshot() {
        log.info("[{}] snapshot",entityId);
        return convert(state);
    }
    
    @Override
    public void handleSnapshot(PcParentDomain.ParentState snapshot) {
        log.info("[{}] handleSnapshot: {}",entityId,snapshot);
       this.state = convert(snapshot);
        
    }
    
    @Override
    public Empty createParent(PcParentApi.CreateParentCommand command, CommandContext ctx) {
        log.info("[{}] createParent: {} ",entityId,command);
        if(state!=null)
            return Empty.getDefaultInstance();
        PcParentDomain.ParentCreated event = PcParentDomain.ParentCreated.newBuilder()
                .setParentId(command.getParentId())
                .setName(command.getName())
                .setEmail(command.getEmail())
                .build();
        ctx.emit(event);
        return Empty.getDefaultInstance();
    }
    
    @Override
    public Empty updateParent(PcParentApi.UpdateParentCommand command, CommandContext ctx) {
       log.info("[{}] updateParent: {} ",entityId,command);
       if(state==null)
           throw ctx.fail("Parent does not exist!");
        PcParentDomain.ParentUpdated event = PcParentDomain.ParentUpdated.newBuilder()
                .setParentId(command.getParentId())
                .setName(command.getName())
                .setEmail(command.getEmail())
                .build();
        ctx.emit(event);
        return Empty.getDefaultInstance();
    }
    
    @Override
    public Empty deleteParent(PcParentApi.DeleteParentCommand command, CommandContext ctx) {
        log.info("[{}] deleteParent",entityId);
        if(state==null)
            return Empty.getDefaultInstance();
        PcParentDomain.ParentDeleted event = PcParentDomain.ParentDeleted.newBuilder()
                .setParentId(command.getParentId())
                .build();
        ctx.emit(event);
        return Empty.getDefaultInstance();
    }
    
    @Override
    public PcParentApi.Parent getParent(PcParentApi.GetParentCommand command, CommandContext ctx) {
        log.info("[{}] getParent",entityId);
        return state;
    }
    
    @Override
    public Empty addChildDevice(PcParentApi.AddChildDeviceCommand command, CommandContext ctx) {
        log.info("[{}] addChildDevice: {} ",entityId,command);
        if(state==null)
            throw ctx.fail("Parent does not exist!");
        if(state.getChildDevicesList().stream().filter(d->d.getDeviceId().equals(command.getDeviceId())).findAny().isPresent())
            return Empty.getDefaultInstance();
        PcParentDomain.ChildDeviceAdded event = PcParentDomain.ChildDeviceAdded.newBuilder()
                .setParentId(command.getParentId())
                .setDeviceId(command.getDeviceId())
                .setChildName(command.getChildName())
                .build();
        ctx.emit(event);
        return Empty.getDefaultInstance();
    }
    
    @Override
    public Empty removeChildDevice(PcParentApi.RemoveChildDeviceCommand command, CommandContext ctx) {
        log.info("[{}] removeChildDevice: {} ",entityId,command);
        if(state==null)
            throw ctx.fail("Parent does not exist!");
        if(!state.getChildDevicesList().stream().filter(d->d.getDeviceId().equals(command.getDeviceId())).findAny().isPresent())
            return Empty.getDefaultInstance();
        PcParentDomain.ChildDeviceRemoved event = PcParentDomain.ChildDeviceRemoved.newBuilder()
                .setParentId(command.getParentId())
                .setDeviceId(command.getDeviceId())
                .build();
        ctx.emit(event);
        return Empty.getDefaultInstance();
    }
    
    @Override
    public void parentCreated(PcParentDomain.ParentCreated event) {
        log.info("[{}] parentCreated: {}",entityId,event);
        state = PcParentApi.Parent.newBuilder()
                .setName(event.getName())
                .setEmail(event.getEmail())
                .build();
    }
    
    @Override
    public void parentUpdated(PcParentDomain.ParentUpdated event) {
        log.info("[{}] parentUpdated: {}",entityId,event);
        state = PcParentApi.Parent.newBuilder()
                .setName(event.getName())
                .setEmail(event.getEmail())
                .addAllChildDevices(state.getChildDevicesList())
                .build();
    }
    
    @Override
    public void parentDeleted(PcParentDomain.ParentDeleted event) {
        log.info("[{}] parentDeleted: {}",entityId,event);
        state = null;
    }
    
    @Override
    public void childDeviceAdded(PcParentDomain.ChildDeviceAdded event) {
       log.info("[{}] childDeviceAdded: {}",entityId,event);
       state = state.toBuilder()
               .addChildDevices(PcParentApi.ChildDevice.newBuilder()
                                           .setDeviceId(event.getDeviceId())
                                           .setChildName(event.getChildName()))
               .build();
    }
    
    @Override
    public void childDeviceRemoved(PcParentDomain.ChildDeviceRemoved event) {
        log.info("[{}] childDeviceRemoved: {}",entityId,event);
        List newList = state.getChildDevicesList().stream().filter(d->!d.getDeviceId().equals(event.getDeviceId())).collect(Collectors.toList());
        state = state.toBuilder()
                .clearChildDevices()
                .addAllChildDevices(newList)
                .build();
    }

    private PcParentApi.Parent convert(PcParentDomain.ParentState state){
       return PcParentApi.Parent.newBuilder()
                .setName(state.getName())
                .setEmail(state.getEmail())
                .addAllChildDevices(state.getChildDevicesList().stream().map(this::convert).collect(Collectors.toList()))
                .build();
    }

    private PcParentApi.ChildDevice convert(PcParentDomain.ChildDeviceState state){
        return PcParentApi.ChildDevice.newBuilder()
                .setDeviceId(state.getDeviceId())
                .setChildName(state.getChildName())
                .build();
    }

    private PcParentDomain.ParentState convert(PcParentApi.Parent state){
        return PcParentDomain.ParentState.newBuilder()
                .setName(state.getName())
                .setEmail(state.getEmail())
                .addAllChildDevices(state.getChildDevicesList().stream().map(this::convert).collect(Collectors.toList()))
                .build();
    }

    private PcParentDomain.ChildDeviceState convert(PcParentApi.ChildDevice state){
        return PcParentDomain.ChildDeviceState.newBuilder()
                .setDeviceId(state.getDeviceId())
                .setChildName(state.getChildName())
                .build();
    }
}