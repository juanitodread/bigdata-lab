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

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


/**
 * Reducer for word-count
 *
 * @author juanitodread
 * @version 1.0
 * @since   1.0
 * 
 * 	        Feb 21, 2016
 */
public class WordCountReducer extends
        Reducer<Text, IntWritable, Text, IntWritable> {
    
    @Override
    public void reduce( Text key,
                        Iterable<IntWritable> values,
                        Context context ) throws IOException,
            InterruptedException {
        int sum = 0;
        
        for( IntWritable value: values ) {
            sum += value.get( );
        }
        
        context.write( key, new IntWritable( sum ) );
    }
}
