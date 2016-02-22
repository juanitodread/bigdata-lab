/**
 * word-count
 *
 * Copyright 2016 juanitodread
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.juanitodread.bigdatalab.wordcount;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


/**
 * Word count job
 *
 * @author juanitodread
 * @version 1.0
 * @since   1.0
 * 
 * 	        Feb 21, 2016
 */
public class WordCountJob {
    
    public static void main( String... args ) throws IOException,
            ClassNotFoundException, InterruptedException {
        
        Job job = Job.getInstance( );
        job.setJarByClass( WordCountJob.class );
        job.setJobName( "Word Count" );
        
        FileInputFormat.addInputPath( job, new Path( args[0] ) );
        FileOutputFormat.setOutputPath( job, new Path( args[1] ) );
        
        job.setMapperClass( WordCountMapper.class );
        job.setReducerClass( WordCountReducer.class );
        
        job.setOutputKeyClass( Text.class );
        job.setOutputValueClass( IntWritable.class );
        
        System.exit( job.waitForCompletion( true ) ? 0: 1 );
    }
}
