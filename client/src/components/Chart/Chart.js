import React from 'react';

import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import { Line } from 'react-chartjs-2';
import './../../index.css'

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
);

const hostname = "http://localhost:8080"
const apiPrefix = hostname + "/api/v1"

//Create static chart Config
export const options = {
  responsive: true,
  plugins: {
    legend: {
      position: 'top',
    },
    title: {
      display: true,
      text: 'Stock price range',
    },
  },
};
function request () {
  return {
    credentials: "omit",
    method: "GET",
    mode: "cors",
  }
}

class Chart extends React.Component {
    componentDidMount() {
        console.log(this.data.data.symbol);
        const endpoint = apiPrefix + "/stock/history?symbol="+this.data.data.symbol+"&from=2023-02-06&to=2023-03-09";
        //Grab data about stock and crete a data object
        fetch(endpoint, request())
            .then(res => {return res.json()})
            .then(data => {
                console.log(data);
                const prices = [];

                data.stockData.forEach((item) => {
                    prices.push(item.price);
                });

                let chartData = {
                    labels: [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20],
                    datasets: [
                        {
                            label: this.data.data.symbol,
                            data: prices,
                            borderColor: 'rgb(255, 99, 132)',
                            backgroundColor: 'rgba(255, 99, 132, 0.5)',
                        }
                    ]
                };
                this.setState({ isLoading: false, stocks: chartData });
                console.log(chartData);
            })
            .catch((error) =>console.log("L + RAITO: "+ error));
    }
    constructor ( data ) {
        super(data);
        this.state = {
            isLoading: true,
            stocks: [],
        };
        this.data = data;
    }
    render(){
        const { isLoading, stocks } = this.state;
        console.log(stocks);
        //print loading while waiting for data
        if (isLoading) {
          return <div>Loading...</div>;
        }
        // create the line element when the data is recieved
        return <Line options={options} data={stocks} />;
    }
}

export default Chart

