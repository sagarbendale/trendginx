function getURLParameter(name) 
{
  return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null
}

function getRequestId()
{

		return getURLParameter('request');
}


$(window).scroll(function() {
    if ($(".navbar").offset().top > 50) {
        $(".navbar-fixed-top").addClass("top-nav-collapse");
    } else {
        $(".navbar-fixed-top").removeClass("top-nav-collapse");
    }
});

// jQuery for page scrolling feature - requires jQuery Easing plugin
$(function() {
    $('a.page-scroll').bind('click', function(event) {
        var $anchor = $(this);
        $('html, body').stop().animate({
            scrollTop: $($anchor.attr('href')).offset().top
        }, 1500, 'easeInOutExpo');
        event.preventDefault();
    });
});

// Closes the Responsive Menu on Menu Item Click
$('.navbar-collapse ul li a').click(function() {
    $('.navbar-toggle:visible').click();
});





function getFavTweets(requestId,limit)
{
        $.getJSON( "api/getTopFavTweets/"+requestId+"/"+limit, function( json ) 
        {               
                     data=json.favtweets;       
                     for (var i=0, len=data.length; i < len; i++) 
                     {
                          $("#fv-twt").append('<tr><td>'+data[i].tweet+'&nbsp;&nbsp;<i class=\"fi-star\"></i>'+data[i].favcount+'</td></tr>')
                      }             
        });

}


 function drawMap(data) 
 {

        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 8,
          center: new google.maps.LatLng(-33.92, 151.25),
          mapTypeId: google.maps.MapTypeId.ROADMAP
        });

         var infowindow = new google.maps.InfoWindow();

         var marker, i;

         for (i = 0; i < data.length; i++) 
         {  
                 var image='js/dashboard/iconbase/green.png';

                 if(data[i].sentiment.toUpperCase() == 'NEGATIVE'.toUpperCase())
                 {
                     image='js/dashboard/iconbase/red.png';
                 }
                
                 marker = new google.maps.Marker({
                   position: new google.maps.LatLng(data[i].lat, data[i].lon ),
                   map: map,
                   icon: image
                 });

                 google.maps.event.addListener(marker, 'click', (function(marker, i) 
                 {
                   return function() 
                   {
                     infowindow.setContent('Sentiment : '+data[i].sentiment);
                     infowindow.open(map, marker);
                   }
                 })(marker, i));
         }
     

     
 }
 
 function getGlobalDist(requestId,limit)
 {
     $.getJSON( "api/getGlobalDistribution/"+requestId+"/"+limit, function( json ) 
                {           
                            drawMap(json.tweets);
                             
                });
     
 }

 function getLatestTweets(requestId,limit)
{
        $.getJSON( "api/getLatestTweets/"+requestId+"/"+limit, function( json ) 
        {               
                     data=json.tweets;      
                     for (var i=0, len=data.length; i < len; i++) 
                     {
                          $("#latest-twt").append('<tr><td><i class=\"fi-torso\"></i>&nbsp;<b>'+data[i].username+'</b>&nbsp;<br> '+data[i].tweet+'</td></tr>')

                     }              
        });

}

function displayNegWords(inputData){
    $('#neg_words').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: ''
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            type: 'category',
            labels: {
                rotation: -45,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Count'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: 'Count : {point.y:.1f}'
        },
        series: [{
            name: 'Count',
            data:inputData,
            dataLabels: {
                enabled: true,
                rotation: -90,
                color: '#FFFFFF',
                align: 'right',
                format: '{point.y:.1f}', // one decimal
                y: 10, // 10 pixels down from the top
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        }]
    });
}




function getNegWords(requestId,limit)
{

    var arr=[];
    $.getJSON( "api/getTopNegativeWords/"+requestId+'/'+limit, function( json ) 
            {               
        
                    var data=json.negativetweets;
                    for (var i=0, len=data.length; i < len; i++) 
                    {
                            arr.push([data[i].word,data[i].count]);
         
                    }
                        
                    displayNegWords(arr)
            });
    

}


function displaySensChart(senTwt) {

    $('#overall-sens').highcharts({

        chart: {
            type: 'solidgauge',
            backgroundColor: 'transparent'
        },

        title: null,

        pane: {
            center: ['50%', '70%'],
            size: '120%',
            startAngle: -90,
            endAngle: 90,
            background: {
                backgroundColor: '#fff',
                innerRadius: '75%',
                outerRadius: '100%',
                shape: 'arc',
                borderColor: 'transparent'
            }
        },

        tooltip: {
            enabled: false
        },

        // the value axis
        yAxis: {
            min: 0,
            max: 100,
            stops: [
                [0.1, '#e74c3c'], // red
                [0.5, '#f1c40f'], // yellow
                [0.9, '#2ecc71'] // green
                ],
            minorTickInterval: null,
            tickPixelInterval: 400,
            tickWidth: 0,
            gridLineWidth: 0,
            gridLineColor: 'transparent',
            labels: {
                enabled: false
            },
            title: {
                enabled: false
            }
        },

        credits: {
            enabled: false
        },

        plotOptions: {
            solidgauge: {
                innerRadius: '75%',
                dataLabels: {
                    y: -45,
                    borderWidth: 0,
                    useHTML: true
                }
            }
        },

        series: [{
            data: [senTwt],
            dataLabels: {
                format: '<p style="text-align:center;">{y}%</p>'
            }
        }]
    });

}

function getSensTweetsOverall(requestId)
{
        $.getJSON( "api/getPercSensTweets/"+requestId, function( json ) 
        {           
            displaySensChart((Number(json.positive) / parseInt(json.totaltweets))*100)
                    
        });

}


function displayOverallSentimentsChart(sentimentsData) {

    // Make monochrome colors and set them as default for all pies
    Highcharts.getOptions().plotOptions.pie.colors = (function () {
        var colors = [],
            base = Highcharts.getOptions().colors[0],
            i;

        for (i = 0; i < 10; i += 1) {
            // Start out with a darkened base color (negative brighten), and end
            // up with a much brighter color
            colors.push(Highcharts.Color(base).brighten((i - 3) / 7).get());
        }
        return colors;
    }());

    // Build the chart
    $('#sentiment-overall').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: ''
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                }
            }
        },
        series: [{
            type: 'pie',
            name: 'Percentage : ',
            data: sentimentsData
        }]
    });
}



function getOverallSentiments(requestId)
{
        
    var arr=[];
    $.getJSON( "api/getOverallSentiments/"+requestId, function( json ) 
            {                               
                        $.each( json, function( key, val ) 
                        {
                                if(key!='totaltweets')
                                {

                                    arr.push([key,val]);
                                }
                        });
                    displayOverallSentimentsChart(arr)
            });
    

}



function displayPosWords(inputData){
    $('#pos_words').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: ''
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            type: 'category',
            labels: {
                rotation: -45,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Count'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: 'Count : {point.y:.1f}'
        },
        series: [{
            name: 'Count',
            data:inputData,
            dataLabels: {
                enabled: true,
                rotation: -90,
                color: '#FFFFFF',
                align: 'right',
                format: '{point.y:.1f}', // one decimal
                y: 10, // 10 pixels down from the top
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        }]
    });
}




function getPosWords(requestId,limit)
{
        
    var arr=[];
    $.getJSON( "api/getTopPositiveWords/"+requestId+'/'+limit, function( json ) 
            {               
        
                    var data=json.positivetweets;
                    for (var i=0, len=data.length; i < len; i++) 
                    {
                            arr.push([data[i].word,data[i].count]);
         
                    }
                        
                    displayPosWords(arr)
            });
    

}




function getTopSensTweets(requestId,limit)
{
        $.getJSON( "api/getTopSenTweets/"+requestId+"/"+limit, function( json ) 
        {               
                     data=json.sentweets;       
                     for (var i=0, len=data.length; i < len; i++) 
                     {
                         $("#sens-twt").append('<tr><td><i class=\"fi-soical-twitter\"></i>&nbsp'+data[i].tweet+'</td></tr>')

                     }              
        });

}

function displaySentimentsByWeek(seriesData) {
    $('#sentiment-by-week').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: ''
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            categories: [
                'Positive',
                'Negative',
                'Neutral'
            ],
            crosshair: true
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Count'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: seriesData
    });
}



function getSentimentsByWeek(requestId)
{
    
    var arr=[];
    $.getJSON( "api/getSentimentsByWeek/"+requestId, function( json ) 
            {                   
        
                    data=json.sentimentsbyweek;     
                    for (var i=0, len=data.length; i < len; i++) 
                    {
                        arr.push({'name':data[i].weekstartdate+" : "+data[i].weekenddate,'data':[data[i].positive,data[i].negative,data[i].neutral]});
                    }       
    
                    displaySentimentsByWeek(arr)
            });
    

}

function getReTweets(requestId,limit)
{
        $.getJSON( "api/getTopReTweets/"+requestId+"/"+limit, function( json ) 
        {               
                     data=json.retweets;        
                     for (var i=0, len=data.length; i < len; i++) 
                     {
                          $("#top-trtwt").append('<tr><td>'+data[i].tweet+'&nbsp;&nbsp;<i class=\"fi-star\"></i>'+data[i].retweetcount+'</td></tr>')
                      }             
        });

}


function getTopTweeters(requestId,limit)
{
        $.getJSON( "api/getTopUsers/"+requestId+"/"+limit, function( json ) 
        {               
                     data=json.users;            
                     for (var i=0, len=data.length; i < len; i++) 
                     {
                            $("#top_twt_users").append('<tr><td><i class=\"fi-torso\"></i>&nbsp;<b>'+data[i].username+'</b> </td><td><i class=\"fi-star\"></i>'+data[i].count+'</td></tr>')

                      }             
        });

}

function plotTweetRate(last,current,projected) {
    $('#tweets-rate').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: 'Tweet Rate'
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            categories: [
                'Month',
                'Week',
                'Day',
                'Hour'
            ],
            crosshair: true
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Rate'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: [{
            name: 'Last',
            data: last

        }, {
            name: 'Current',
            data: current

        }, {
            name: 'Projected',
            data: projected

        }
        ]
    });
}

function getTweetRate(requestId)
{   
    
    $.getJSON( "api/getTweetRate/"+requestId, function( json ) 
        {                               
                    

         var last=[json.ratelastmonth,json.ratelastweek,json.ratelastday,json.ratelasthour];
         var current=[json.ratecurrentmonth,json.ratecurrentweek,json.ratecurrentday,json.ratecurrenthour];
         var projected=[json.ratenextmonth,json.ratenextweek,json.ratenextday,json.ratenexthour];

        plotTweetRate(last,current,projected)
        });
    

}




function submitRequest(hashTag,emailId)
{
            $.get( "api/submitRequest/"+hashTag+"/"+emailId, function( result ) 
            {             
             $('#reqForm').empty();              
            $('#reqForm').html("<h5> Thank You. Your request has been submitted. You will shortly receive an email containing dashboard url.</h5>")
            });
}


function generateDashboard()
{
            var req=getRequestId();
              
              if(req > 0)
              {
              getTopTweeters(req,10);
              getFavTweets(req,10);
              getReTweets(req,10);
              getLatestTweets(req,10);
              getOverallSentiments(req);
              getSentimentsByWeek(req);
              getNegWords(req,18)
              getPosWords(req,18);
              getGlobalDist(req,100);
              getTweetRate(req);
              getSensTweetsOverall(req);
              getTopSensTweets(req,100);
              }
              else
            {
                  alert('No Request Found')
            }
}


