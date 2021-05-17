const fs = require('fs');
const readline = require('readline');
var stream = require('stream');
const { PerformanceObserver, performance } = require('perf_hooks');

const express = require('express');
const { send } = require('process');
const app = express()
const port = 3000;


const pathCSV = 'dial7.csv';
var data = [];
var busiestDate = new Map(), busiestPickup = new Map(), busiestState = new Map(),
    busiestStreet = new Map(), earliestTime, latestTime, busiestTime;
var instream;
var outstream;
var rl;

///////////////////////////////////////////////////////
//Server Start

parseCSV();
app.get('/', (req, res) => {
    res.send('Hello World!')
})

app.get('/search/', (req,res) => {
    var ret;
    console.log('Request received: ' + req.header('column') + ', ' + req.header('entry'));
    
    if(req.header('column') == 'all'){
       
        ret = data;
    
    }
    else{
        const column = req.header('column');
         const entry = req.header('entry');
        ret = search(data,column,entry);
        
    } 

    res.send(JSON.stringify(ret));
    
   
})

app.get('/analysis/', (req,res) => {
    var ret;
    console.log('Analysis request received for last search.');
    
    if(req.header('column') == 'all'){
        
        var t0 = performance.now();


        if(busiestDate.size == 0){
            busiestDate = mode0(data,'date');
            busiestTime = mode0(data, 'time');
            busiestPickup = mode0(data,'pickup');
            busiestState = mode0(data,'state');
            busiestStreet = mode0(data,'street');
            earliestTime = findEarliestTime(data);
            latestTime = findLatestTime(data);

        }
        ret = new Analysis( 'Busiest Date: ' + busiestDate['max'] + ' | Occurrences: ' + busiestDate[busiestDate['max']], 
                    'Busiest time: ' + busiestTime['max'] + ' | Occurrences: ' + busiestTime[busiestTime['max']], 
                    'Busiest state: ' + busiestState['max'] + ' | Occurrences: ' + busiestState[busiestState['max']], 
                    'Busiest pickup: ' + busiestPickup['max'] + ' | Occurrences: ' + busiestPickup[busiestPickup['max']], 
                    'Busiest street: ' + busiestStreet['max'] + ' | occurrences: ' + busiestStreet[busiestStreet['max']], 
                    earliestTime, latestTime);
        
        var t1 = performance.now();
        console.log('Time taken to execute analysis: ' + (t1-t0).toString());
    
    }
    else{
        const column = req.header('column');
         const entry = req.header('entry');
        ret = analysis(search(data,column,entry));
        
    } 
    res.send(JSON.stringify(ret));
})

app.post('/insert/', (req,res) =>{
    console.log('Insert request received.');
    var u = new Uber(req.header('date'), req.header('time'), req.header('state'), req.header('pickup'), 
                req.header('address'), req.header('street'), (data.length+1).toString() );
    
    data.push(u);
    res.send(data.length.toString());
    increment('date', req.header('date'));
    increment('time', req.header('time'));
    increment('state', req.header('state'));
    increment('pickup', req.header('pickup'));
    increment('address', req.header('address'));
    increment('street', req.header('street'));
})

app.post('/edit/', (req,res) =>{
    console.log('Edit request received.');
    if(req.header('date') != data[req.header('id').date]){
        decrement('date', data[req.header('id')].date);
        increment('date', req.header('date'));
    }
    if(req.header('time') != data[req.header('id').time]){
        decrement('time', data[req.header('id')].time);
        increment('time', req.header('time'));
    }
    if(req.header('state') != data[req.header('id').state]){
        decrement('state', data[req.header('id')].state);
        increment('state', req.header('state'));
    }
    if(req.header('pickup') != data[req.header('id').pickup]){
        decrement('pickup', data[req.header('id')].pickup);
        increment('pickup', req.header('pickup'));
    }
    if(req.header('address') != data[req.header('id').address]){
        decrement('address', data[req.header('id')].address);
        increment('address', req.header('address'));
    }
    if(req.header('street') != data[req.header('id').street]){
        decrement('street', data[req.header('id')].street);
        increment('street', req.header('street'));
    }
    data[req.header('id')] = new Uber(req.header('date'), req.header('time'), req.header('state'), req.header('pickup'), 
                req.header('address'), req.header('street'), req.header('id') );

    res.send("Success");
    
    
})

app.post('/remove/', (req,res) =>{
    console.log('Remove request received.');
    data[req.header('index')] = null;
    res.send("Success");
    decrement('date', req.header('date'));
    decrement('time', req.header('time'));
    decrement('state', req.header('state'));
    decrement('pickup', req.header('pickup'));
    decrement('address', req.header('address'));
    decrement('street', req.header('street'));
    
})
  
app.listen(port, () => {
    console.log(`App listening at http://localhost:${port}`)
})



//Server End
///////////////////////////////////////////////////////

class Uber{
    date;
    time;
    state;
    pickup;
    address;
    street;
    fulladdress;
    id;
    constructor(date,time,state,pickup,address,street, id){
        this.date = date;
        this.time = time;
        this.state = state;
        this.pickup = pickup;
        this.address = address;
        this.street = street;
        this.id = id;
        this.fulladdress = address + ' ' + street;

    }

}

class Analysis{
    Busiest_Date;
    Busiest_Time;
    Busiest_State;
    Busiest_Pickup;
    Busiest_Street;
    Earliest_Time;
    Latest_time;
    entry;
    constructor(bd,bt,bse,bp,bst,et,lt){
        this.Busiest_Date=bd;
        this.Busiest_Time=bt;
        this.Busiest_State=bse;
        this.Busiest_Pickup=bp;
        this.Busiest_Street=bst;
        this.Earliest_Time=et;
        this.Latest_time=lt;
        this.entry = 
            bd + '\n' + 
            bt+ '\n' +
            bse+ '\n' +
            bp+ '\n' +
            bst+ '\n' +
            et+ '\n' +
            lt;
    }
}




function parseCSV(){

    instream = fs.createReadStream(pathCSV);
    outstream = new stream();
    rl = readline.createInterface(instream, outstream);
    var splitLine;
    var ret = [];
    
    rl.on('line', function(line) {
        
        splitLine = line.split(",");
                
        while(splitLine.length < 6){
            splitLine.push("N/A");
        }
        
        for(var j = 0; j < splitLine.length; j++){
            if(splitLine[j].length < 2){
                splitLine[j] = "N/A";
            }
            
        }
        var uber = new Uber(splitLine[0].replace("\\s{2,}", " ").trim(), splitLine[1].replace("\\s{2,}", " ").trim(), splitLine[2].replace("\\s{2,}", " ").trim(),
            splitLine[3].replace("\\s{2,}", " ").trim(), splitLine[4].replace("\\s{2,}", " ").trim(), splitLine[5].replace("\\s{2,}", " ").trim(), ret.length.toString());
        console.log
        ret.push(uber);

    
        
    });

    rl.on('close', function(){
        ret.shift();
        data = ret;
    });
        
     
}



 function search(arr, column, text){
     var res = [];
     switch(column){
        case "date":
            for(var i = 0; i < arr.length; i++){
                if(arr[i].date == text){
                    res.push(arr[i]);
                }
            }
            break;
        case "time":
            for(var i = 0; i < arr.length; i++){
                if(arr[i].time == text){
                    res.push(arr[i]);
                }
            }
            break;
        case "state":
            for(var i = 0; i < arr.length; i++){
                if(arr[i].state == text){
                    res.push(arr[i]);
                }
            }
            break;
        case "pickup":
            for(var i = 0; i < arr.length; i++){
                if(arr[i].pickup == text){
                    res.push(arr[i]);
                }
            }
            break;
        case "address":
            for(var i = 0; i < arr.length; i++){
                if(arr[i].address == text){
                    res.push(arr[i]);
                }
            }
            break;
        case "street":
            for(var i = 0; i < arr.length; i++){
                if(arr[i].street == text){
                    res.push(arr[i]);
                }
            }
            break;
        case "full address":
            for(var i = 0; i < arr.length; i++){
                if(arr[i].fulladdress == text){
                    res.push(arr[i]);
                }
            }
            break;
        default:
            break;
     }
     return res;
 }



function timeHelper(string){
     string = string.replace(':', '.');
     return string;
 }

function findEarliestTime(arr){
     ret = arr[0].time;
     for(var i = 0; i < arr.length; i++){
        if(parseFloat(timeHelper(arr[i].time)) < parseFloat(timeHelper(ret))){
            ret = arr[i].time;
        }
     }
     return "Earliest time: " + ret;
 }

function findLatestTime(arr){
    ret = arr[0].time;
    for(var i = 0; i < arr.length; i++){
       if(parseFloat(timeHelper(arr[i].time)) > parseFloat(timeHelper(ret))){
           ret = arr[i].time;
       }
    }
    return "Latest time: " + ret;
}
 function mode(array, column)
{
    if(array.length == 0)
    return null;
    var modeMap = {};
    var maxEl = array[0], maxCount = 1;

    switch(column){
        case "date":
            for(var i = 0; i < array.length; i++)
            {
                var el = array[i].date;
                if(modeMap[el] == null)
                    modeMap[el] = 1;
                else
                    modeMap[el]++;  
                if(modeMap[el] > maxCount)
                {
                    maxEl = el;
                    maxCount = modeMap[el];
                }
            }
            break;
        case "time":
            for(var i = 0; i < array.length; i++)
            {
                var el = array[i].time;
                if(modeMap[el] == null)
                    modeMap[el] = 1;
                else
                    modeMap[el]++;  
                if(modeMap[el] > maxCount)
                {
                    maxEl = el;
                    maxCount = modeMap[el];
                }
            }
            break;
        case "state":
            for(var i = 0; i < array.length; i++)
            {
                var el = array[i].state;
                if(modeMap[el] == null)
                    modeMap[el] = 1;
                else
                    modeMap[el]++;  
                if(modeMap[el] > maxCount)
                {
                    maxEl = el;
                    maxCount = modeMap[el];
                }
            }
            break;
        case "pickup":
            for(var i = 0; i < array.length; i++)
            {
                var el = array[i].pickup;
                if(modeMap[el] == null)
                    modeMap[el] = 1;
                else
                    modeMap[el]++;  
                if(modeMap[el] > maxCount)
                {
                    maxEl = el;
                    maxCount = modeMap[el];
                }
            }
            break;

        case "street":
            for(var i = 0; i < array.length; i++)
            {
                var el = array[i].street;
                if(modeMap[el] == null)
                    modeMap[el] = 1;
                else
                    modeMap[el]++;  
                if(modeMap[el] > maxCount)
                {
                    maxEl = el;
                    maxCount = modeMap[el];
                }
            }
            break;

        default:
            break;
     }
   
    
    return 'Busiest ' + column + ': ' + maxEl + ' | Occurrences: ' + maxCount;
}

function mode0(array, column)
{
    
    if(array.length == 0)
    return null;
    var modeMap = {};
    var maxEl = array[0], maxCount = 1;

    switch(column){
        case "date":
            for(var i = 0; i < array.length; i++)
            {
                var el = array[i].date;
                if(modeMap[el] == null)
                    modeMap[el] = 1;
                else
                    modeMap[el]++;  
                if(modeMap[el] > maxCount)
                {
                    maxEl = el;
                    maxCount = modeMap[el];
                }
            }
            break;
        case "time":
            for(var i = 0; i < array.length; i++)
            {
                var el = array[i].time;
                if(modeMap[el] == null)
                    modeMap[el] = 1;
                else
                    modeMap[el]++;  
                if(modeMap[el] > maxCount)
                {
                    maxEl = el;
                    maxCount = modeMap[el];
                }
            }
            break;
        case "state":
            for(var i = 0; i < array.length; i++)
            {
                var el = array[i].state;
                if(modeMap[el] == null)
                    modeMap[el] = 1;
                else
                    modeMap[el]++;  
                if(modeMap[el] > maxCount)
                {
                    maxEl = el;
                    maxCount = modeMap[el];
                }
            }
            break;
        case "pickup":
            for(var i = 0; i < array.length; i++)
            {
                var el = array[i].pickup;
                if(modeMap[el] == null)
                    modeMap[el] = 1;
                else
                    modeMap[el]++;  
                if(modeMap[el] > maxCount)
                {
                    maxEl = el;
                    maxCount = modeMap[el];
                }
            }
            break;

        case "street":
            for(var i = 0; i < array.length; i++)
            {
                var el = array[i].street;
                if(modeMap[el] == null)
                    modeMap[el] = 1;
                else
                    modeMap[el]++;  
                if(modeMap[el] > maxCount)
                {
                    maxEl = el;
                    maxCount = modeMap[el];
                }
            }
            break;

        default:
            break;
     }
   
     modeMap['max'] = maxEl;
    
    
     return modeMap;
    ;
}

function analysis(arr){

    var anal = new Analysis( 
        mode(arr, "date"), mode(arr, "time"), mode(arr, "state"),
        mode(arr, "pickup"), mode(arr, "street"), findEarliestTime(arr), findLatestTime(arr) 
        );
    return anal;
}

function increment(column, key){
    
    switch(column){
        case "date":
            busiestDate[key] += 1;
            if(busiestDate[key] > busiestDate['max']){
                busiestDate['max'] = key;
            }
            break;
        case "time":
            busiestTime[key] += 1;
            if(busiestTime[key] > busiestTime['max']){
                busiestTime['max'] = key;
            }
            break;
        case "state":
            busiestState[key] += 1;
            if(busiestState[key] > busiestState['max']){
                busiestState['max'] = key;
            }
            break;
        case "pickup":
            busiestPickup[key] += 1;
            if(busiestPickup[key] > busiestPickup['max']){
                busiestPickup['max'] = key;
            }
            break;
            
        case "street":
            busiestStreet[key] += 1;
            if(busiestStreet[key] > busiestStreet['max']){
                busiestStreet['max'] = key;
            }
            break;

        default:
            break;
     }
}

function decrement(column, key){
    
    switch(column){
        case "date":
            busiestDate[key] -= 1;
            if(busiestDate[key] > busiestDate['max']){
                busiestDate['max'] = key;
            }
            break;
        case "time":
            busiestTime[key] -= 1;
            if(busiestTime[key] > busiestTime['max']){
                busiestTime['max'] = key;
            }
            break;
        case "state":
            busiestState[key] -= 1;
            if(busiestState[key] > busiestState['max']){
                busiestState['max'] = key;
            }
            break;
        case "pickup":
            busiestPickup[key] -= 1;
            if(busiestPickup[key] > busiestPickup['max']){
                busiestPickup['max'] = key;
            }
            break;
            
        case "street":
            busiestStreet[key] -= 1;
            if(busiestStreet[key] > busiestStreet['max']){
                busiestStreet['max'] = key;
            }
            break;

        default:
            break;
     }
}