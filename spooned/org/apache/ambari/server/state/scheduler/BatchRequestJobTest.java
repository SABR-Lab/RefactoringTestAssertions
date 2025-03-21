package org.apache.ambari.server.state.scheduler;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Trigger;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.captureLong;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createMockBuilder;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
public class BatchRequestJobTest {
    @org.junit.Test
    public void testDoWork() throws java.lang.Exception {
        org.apache.ambari.server.scheduler.ExecutionScheduleManager scheduleManagerMock = org.easymock.EasyMock.createMock(org.apache.ambari.server.scheduler.ExecutionScheduleManager.class);
        org.apache.ambari.server.state.scheduler.BatchRequestJob batchRequestJob = new org.apache.ambari.server.state.scheduler.BatchRequestJob(scheduleManagerMock, 100L);
        java.lang.String clusterName = "mycluster";
        long requestId = 11L;
        long executionId = 31L;
        long batchId = 1L;
        java.util.Map<java.lang.String, java.lang.Object> properties = new java.util.HashMap<>();
        properties.put(org.apache.ambari.server.state.scheduler.BatchRequestJob.BATCH_REQUEST_EXECUTION_ID_KEY, executionId);
        properties.put(org.apache.ambari.server.state.scheduler.BatchRequestJob.BATCH_REQUEST_BATCH_ID_KEY, batchId);
        properties.put(org.apache.ambari.server.state.scheduler.BatchRequestJob.BATCH_REQUEST_CLUSTER_NAME_KEY, clusterName);
        java.util.HashMap<java.lang.String, java.lang.Integer> taskCounts = new java.util.HashMap<java.lang.String, java.lang.Integer>() {
            {
                put(org.apache.ambari.server.state.scheduler.BatchRequestJob.BATCH_REQUEST_FAILED_TASKS_KEY, 0);
                put(org.apache.ambari.server.state.scheduler.BatchRequestJob.BATCH_REQUEST_FAILED_TASKS_IN_CURRENT_BATCH_KEY, 0);
                put(org.apache.ambari.server.state.scheduler.BatchRequestJob.BATCH_REQUEST_TOTAL_TASKS_KEY, 0);
            }
        };
        org.apache.ambari.server.state.scheduler.BatchRequestResponse pendingResponse = new org.apache.ambari.server.state.scheduler.BatchRequestResponse();
        pendingResponse.setStatus(org.apache.ambari.server.actionmanager.HostRoleStatus.PENDING.toString());
        org.apache.ambari.server.state.scheduler.BatchRequestResponse inProgressResponse = new org.apache.ambari.server.state.scheduler.BatchRequestResponse();
        inProgressResponse.setStatus(org.apache.ambari.server.actionmanager.HostRoleStatus.IN_PROGRESS.toString());
        org.apache.ambari.server.state.scheduler.BatchRequestResponse completedResponse = new org.apache.ambari.server.state.scheduler.BatchRequestResponse();
        completedResponse.setStatus(org.apache.ambari.server.actionmanager.HostRoleStatus.COMPLETED.toString());
        org.easymock.Capture<java.lang.Long> executionIdCapture = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<java.lang.Long> batchIdCapture = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<java.lang.String> clusterNameCapture = org.easymock.EasyMock.newCapture();
        org.easymock.EasyMock.expect(scheduleManagerMock.executeBatchRequest(org.easymock.EasyMock.captureLong(executionIdCapture), org.easymock.EasyMock.captureLong(batchIdCapture), org.easymock.EasyMock.capture(clusterNameCapture))).andReturn(requestId);
        org.easymock.EasyMock.expect(scheduleManagerMock.getBatchRequestResponse(requestId, clusterName)).andReturn(pendingResponse).times(2);
        org.easymock.EasyMock.expect(scheduleManagerMock.getBatchRequestResponse(requestId, clusterName)).andReturn(inProgressResponse).times(4);
        org.easymock.EasyMock.expect(scheduleManagerMock.getBatchRequestResponse(requestId, clusterName)).andReturn(completedResponse).once();
        org.easymock.EasyMock.expect(scheduleManagerMock.hasToleranceThresholdExceeded(executionId, clusterName, taskCounts)).andReturn(false);
        scheduleManagerMock.updateBatchRequest(org.easymock.EasyMock.eq(executionId), org.easymock.EasyMock.eq(batchId), org.easymock.EasyMock.eq(clusterName), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.scheduler.BatchRequestResponse.class), org.easymock.EasyMock.eq(true));
        org.easymock.EasyMock.expectLastCall().anyTimes();
        org.easymock.EasyMock.replay(scheduleManagerMock);
        batchRequestJob.doWork(properties);
        org.easymock.EasyMock.verify(scheduleManagerMock);
        org.junit.Assert.assertEquals(executionId, executionIdCapture.getValue().longValue());
        org.junit.Assert.assertEquals(batchId, batchIdCapture.getValue().longValue());
        org.junit.Assert.assertEquals(clusterName, clusterNameCapture.getValue());
    }

    @org.junit.Test
    public void testTaskCountsPersistedWithTrigger() throws java.lang.Exception {
        org.apache.ambari.server.scheduler.ExecutionScheduleManager scheduleManagerMock = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.scheduler.ExecutionScheduleManager.class);
        org.apache.ambari.server.state.scheduler.BatchRequestJob batchRequestJobMock = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.state.scheduler.BatchRequestJob.class).withConstructor(scheduleManagerMock, 100L).addMockedMethods("doWork").createMock();
        org.quartz.JobExecutionContext executionContext = org.easymock.EasyMock.createNiceMock(org.quartz.JobExecutionContext.class);
        org.quartz.JobDataMap jobDataMap = org.easymock.EasyMock.createNiceMock(org.quartz.JobDataMap.class);
        org.quartz.JobDetail jobDetail = org.easymock.EasyMock.createNiceMock(org.quartz.JobDetail.class);
        java.util.Map<java.lang.String, java.lang.Object> properties = new java.util.HashMap<>();
        properties.put(org.apache.ambari.server.state.scheduler.BatchRequestJob.BATCH_REQUEST_FAILED_TASKS_KEY, 10);
        properties.put(org.apache.ambari.server.state.scheduler.BatchRequestJob.BATCH_REQUEST_TOTAL_TASKS_KEY, 20);
        org.easymock.EasyMock.expect(scheduleManagerMock.continueOnMisfire(executionContext)).andReturn(true);
        org.easymock.EasyMock.expect(executionContext.getMergedJobDataMap()).andReturn(jobDataMap);
        org.easymock.EasyMock.expect(executionContext.getJobDetail()).andReturn(jobDetail);
        org.easymock.EasyMock.expect(jobDetail.getKey()).andReturn(org.quartz.JobKey.jobKey("testJob", "testGroup"));
        org.easymock.EasyMock.expect(jobDataMap.getWrappedMap()).andReturn(properties);
        org.easymock.EasyMock.expect(jobDataMap.getString(((java.lang.String) (org.easymock.EasyMock.anyObject())))).andReturn("testJob").anyTimes();
        org.easymock.Capture<org.quartz.Trigger> triggerCapture = org.easymock.EasyMock.newCapture();
        scheduleManagerMock.scheduleJob(org.easymock.EasyMock.capture(triggerCapture));
        org.easymock.EasyMock.expectLastCall().once();
        org.easymock.EasyMock.replay(scheduleManagerMock, executionContext, jobDataMap, jobDetail);
        batchRequestJobMock.execute(executionContext);
        org.easymock.EasyMock.verify(scheduleManagerMock, executionContext, jobDataMap, jobDetail);
        org.quartz.Trigger trigger = triggerCapture.getValue();
        org.junit.Assert.assertNotNull(trigger);
        org.quartz.JobDataMap savedMap = trigger.getJobDataMap();
        org.junit.Assert.assertNotNull(savedMap);
        org.junit.Assert.assertEquals(10, savedMap.getIntValue(org.apache.ambari.server.state.scheduler.BatchRequestJob.BATCH_REQUEST_FAILED_TASKS_KEY));
        org.junit.Assert.assertEquals(20, savedMap.getIntValue(org.apache.ambari.server.state.scheduler.BatchRequestJob.BATCH_REQUEST_TOTAL_TASKS_KEY));
    }
}
