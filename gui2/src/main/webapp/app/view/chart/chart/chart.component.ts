/*
 * Copyright 2018-present Open Networking Foundation
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
 */
import { Component, OnInit, OnDestroy} from '@angular/core';
import { WebSocketService, LogService } from '../../../../gui2-fw-lib/src/public_api';
import { Time } from '@angular/common';
import { adjustBlueprintForNewNode } from '@angular/core/src/render3/instructions';
import { graphic } from 'echarts';

@Component({
    selector: 'onos-chart',
    templateUrl: './chart.component.html',
    styleUrls: ['./chart.component.css']
})

export class ChartComponent implements OnInit, OnDestroy {
  
    options: any;
    updateOptions: any;
  
    private oneSecond = 1000;
    private now: Date;
    private value: number ;
    private data: any[];
    private data2: any[];
    private timer: any;
    private handlers:string[] = [];
    constructor(
        protected wss: WebSocketService,
        protected log: LogService
    ) { 
        this.log.debug('Chart Component constructed') 
    }
  
    ngOnInit() {
      this.wss.bindHandlers(new Map<string, (data) => void>([
            ['predictRespond', (data) => {
                    this.showResult(data);
                    // this.log.debug('showDetails received', data);
                }
            ]
      ]));
      this.handlers.push('predictRespond')
      
    

      // generate some random testing data:
      this.data = [];
      this.data2 = [];
      this.now = new Date();
      
    //  this.value = Math.random() * 1000;
  
    //   for (let i = 0; i < 60; i++) {
    //     this.data.push(this.randomData());
    //   }
  
      // initialize chart options:
      this.options = {
        title: {
          text: '(ovs1_1,ovs2_1)链路负载预测结果',
          x: 'center'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            animation: false
          }
        },
        legend: {
            data: ['真实值','预测值'],
            x: 'center',
            y: 'bottom'
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          splitLine: {
            show: false
          }
        },
        yAxis: {
          type: 'value',
          boundaryGap: [0, '100%'],
          splitLine: {
            show: false
          }
        },
        series: [{
          name: '真实值',
          type: 'line',
          color: new graphic.LinearGradient(
            0, 0, 0, 1,
            [
              { offset: 0, color: '#2378f7' },
              { offset: 0.7, color: '#2378f7' },
              { offset: 1, color: '#83bff6' }
            ]
          ),

          showSymbol: false,
          hoverAnimation: false,
          data: this.data
        },
        {
          name: '预测值',
          type: 'line',
          color: new graphic.LinearGradient(
            0, 0, 0, 1,
            [
              { offset: 0, color: '#FF0000' },
              { offset: 0.7, color: '#FF5151' },
              { offset: 1, color: '#FF9797' }
            ]
          ),
          showSymbol: false,
          hoverAnimation: false,
          data: this.data2
        }]
      };
      
      this.log.debug('Chart Component initialized')  
      // Mock dynamic data:
      this.timer = setInterval(() => {
        this.log.debug('Request predict result')
        this.wss.sendEvent('predictRequest',new Object)
      }, 5000);
    }
  
    ngOnDestroy() {
         clearInterval(this.timer);
         this.wss.unbindHandlers(this.handlers);
    }
  
    randomData() {
      this.now = new Date(this.now.getTime() + this.oneSecond);
      this.value = this.value + Math.random() * 21 - 10;
      //this.log.error([this.now.getHours(), this.now.getMinutes(), this.now.getSeconds()].join(':'))
      return {
        name: this.now.toString(),
        value: [
          [this.now.getHours(), this.now.getMinutes(), this.now.getSeconds()].join(':'),
          Math.round(this.value)
        ]
      };
    }
    
    nextData(result:number,predict:boolean) {
        this.now = new Date(this.now.getTime() + (predict? (5 * this.oneSecond):0));
        this.value = result;
        return {
          name: this.now.toString(),
          value: [
            [this.now.getHours(), this.now.getMinutes(), this.now.getSeconds()].join(':'),
            Math.round(this.value)
          ]
        };
      }
    showResult(data: any){
        var predict = data['predict'];
        var real = data['Real'];
        this.log.debug(this.now.toString(),' Real value:',real)
        this.log.debug(new Date(this.now.getTime() + 5000).toString(),' Predit value:',predict)

        if(this.data.length>1200){
            this.data.shift();
        }
        this.data.push(this.nextData(real,false));

        if(this.data2.length>1201){
            this.data2.shift();
        }
        this.data2.push(this.nextData(predict,true));

        //this.now = new Date(this.now.getTime() + 5000);

        this.updateOptions = {
            xAxis: {
            },
            series: [{
                    data: this.data
                },
                {
                    data: this.data2
                },]
            };
    }
  }
