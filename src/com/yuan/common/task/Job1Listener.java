/* 
 * Copyright 2005 OpenSymphony 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 * 
 */

package com.yuan.common.task;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Job1Listener implements JobListener {

	private static final Logger log = LoggerFactory.getLogger(Job1Listener.class);
    
    public String getName() {
        return "job1_to_job2";
    }
    
    //运行前执行
    public void jobToBeExecuted(JobExecutionContext inContext) {
        log.info("111111111111111111111");
    }

    public void jobExecutionVetoed(JobExecutionContext inContext) {
        log.info("2222222222222222222222");
    }

    //运行后执行
    public void jobWasExecuted(JobExecutionContext inContext,
            JobExecutionException inException) {
        log.info("333333333333333333333.");
        JobDataMap jdm = inContext.getJobDetail().getJobDataMap();
        System.out.println("=====" + jdm.get("test"));
    }

}
