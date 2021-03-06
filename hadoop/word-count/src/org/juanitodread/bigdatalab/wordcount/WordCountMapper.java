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
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


/**
 * Mapper for word-count
 *
 * @author juanitodread
 * @version 1.0
 * @since   1.0
 * 
 * 	        Feb 21, 2016
 */
public class WordCountMapper extends
        Mapper<LongWritable, Text, Text, IntWritable> {
    private static final IntWritable one = new IntWritable(1);
    private Text word = new Text();
    
    @Override
    public void map( LongWritable key,
                     Text value,
                     Context context ) throws IOException, InterruptedException {
        String line = value.toString( );
        StringTokenizer stringTokenizer = new StringTokenizer( line, " " );
        
        while( stringTokenizer.hasMoreTokens( ) ) {
            word.set( stringTokenizer.nextToken( ) );
            context.write( word, one );
        }
    }
}
