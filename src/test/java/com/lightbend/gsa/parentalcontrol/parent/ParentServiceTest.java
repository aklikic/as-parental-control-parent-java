package com.lightbend.gsa.parentalcontrol.parent;

import com.akkaserverless.javasdk.eventsourcedentity.CommandContext;
import org.junit.Test;
import org.mockito.*;

public class ParentServiceTest {
    private String entityId = "entityId1";
    private ParentServiceImpl entity;
    private CommandContext context = Mockito.mock(CommandContext.class);
    
    @Test
    public void createParentTest() {
        entity = new ParentServiceImpl(entityId);
        
        // TODO: you may want to set fields in addition to the entity id
        //    entity.createParent(Service.CreateParentCommand.newBuilder().setEntityId(entityId).build(), context);
        
        // TODO: if you wish to verify events:
        //    Mockito.verify(context).emit(event);
    }
    
    @Test
    public void updateParentTest() {
        entity = new ParentServiceImpl(entityId);
        
        // TODO: you may want to set fields in addition to the entity id
        //    entity.updateParent(Service.UpdateParentCommand.newBuilder().setEntityId(entityId).build(), context);
        
        // TODO: if you wish to verify events:
        //    Mockito.verify(context).emit(event);
    }
    
    @Test
    public void deleteParentTest() {
        entity = new ParentServiceImpl(entityId);
        
        // TODO: you may want to set fields in addition to the entity id
        //    entity.deleteParent(Service.DeleteParentCommand.newBuilder().setEntityId(entityId).build(), context);
        
        // TODO: if you wish to verify events:
        //    Mockito.verify(context).emit(event);
    }
    
    @Test
    public void getParentTest() {
        entity = new ParentServiceImpl(entityId);
        
        // TODO: you may want to set fields in addition to the entity id
        //    entity.getParent(Service.GetParentCommand.newBuilder().setEntityId(entityId).build(), context);
        
        // TODO: if you wish to verify events:
        //    Mockito.verify(context).emit(event);
    }
    
    @Test
    public void addChildDeviceTest() {
        entity = new ParentServiceImpl(entityId);
        
        // TODO: you may want to set fields in addition to the entity id
        //    entity.addChildDevice(Service.AddChildDeviceCommand.newBuilder().setEntityId(entityId).build(), context);
        
        // TODO: if you wish to verify events:
        //    Mockito.verify(context).emit(event);
    }
    
    @Test
    public void removeChildDeviceTest() {
        entity = new ParentServiceImpl(entityId);
        
        // TODO: you may want to set fields in addition to the entity id
        //    entity.removeChildDevice(Service.RemoveChildDeviceCommand.newBuilder().setEntityId(entityId).build(), context);
        
        // TODO: if you wish to verify events:
        //    Mockito.verify(context).emit(event);
    }
}