const fs = require('fs');
const readline = require('readline');
var stream = require('stream');

const express = require('express');
const { send } = require('process');
const app = express()
const port = 3000;


const pathCSV = 'dial7.csv';
var data = [];
var lastRequest;
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
    console.log('Request received: ' + req.header('column') + ', ' + req.header('entry'));
    const column = req.header('column');
    const entry = req.header('entry');
    var ret = search(data,column,entry);

    res.send(JSON.stringify(ret));
    lastRequest = analysis(ret);
   
})

app.get('/analysis/', (req,res) => {
    console.log('Analysis request received for last search.');
    res.send(JSON.stringify(lastRequest));
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
    entry;
    constructor(date,time,state,pickup,address,street){
        this.date = date;
        this.time = time;
        this.state = state;
        this.pickup = pickup;
        this.address = address;
        this.street = street;
        this.fulladdress = address + ' ' + street;
        this.entry = this.date + ' ' + this.time + ' ' + this.state + ' ' + this.pickup + ' '
            + this.address + ' ' + this.street;

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
            splitLine[3].replace("\\s{2,}", " ").trim(), splitLine[4].replace("\\s{2,}", " ").trim(), splitLine[5].replace("\\s{2,}", " ").trim());
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

function analysis(arr){

    var anal = new Analysis( 
        mode(arr, "date"), mode(arr, "time"), mode(arr, "state"),
        mode(arr, "pickup"), mode(arr, "street"), findEarliestTime(arr), findLatestTime(arr) 
        );
    return anal;
}