import { Component } from '@angular/core';
import { ViewChild, ChangeDetectionStrategy } from '@angular/core';
import { Chart, registerables } from 'chart.js';
// Import the annotation plugin tool
import AnnotationPlugin from 'chartjs-plugin-annotation';
import { ChartConfiguration, ChartEvent, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { MatButton } from '@angular/material/button';
import { PortfolioSnapshotsService } from '../../services/portfolio-snapshots.service';

// 2. REGISTER EVERYTHING IMMEDIATELY
Chart.register(...registerables, AnnotationPlugin);



@Component({
  selector: 'app-market-chart',
  standalone: true,
  imports: [MatButton, BaseChartDirective],
  templateUrl: './market-chart.component.html',
  styleUrl: './market-chart.component.css'
})
export class MarketChartComponent {
  // Reference the chart directive so we can trigger a redraw when API data arrives
  @ViewChild(BaseChartDirective) chart?: BaseChartDirective;
  public lineChartType: ChartType = 'line';

  constructor(private portfolioService: PortfolioSnapshotsService) { }

  ngOnInit(): void {
    this.loadPortfolioSnapshots();
  }



  public lineChartData: ChartConfiguration['data'] = {
    datasets: [
      {
        data: [],
        label: 'Portfolio Value (₹)',
        backgroundColor: 'rgba(16, 185, 129, 0.2)', // Emerald fill
        borderColor: '#10b981',                     // Emerald line
        pointBackgroundColor: '#10b981',
        pointBorderColor: '#fff',
        pointHoverBackgroundColor: '#fff',
        pointHoverBorderColor: 'rgba(16, 185, 129, 0.8)',
        fill: true,
      },
      {
        data: [],
        label: 'Cash Balance (₹)',
        backgroundColor: 'rgba(56, 189, 248, 0.2)', // Sky blue fill
        borderColor: '#38bdf8',                     // Sky blue line
        pointBackgroundColor: '#38bdf8',
        pointBorderColor: '#fff',
        pointHoverBackgroundColor: '#fff',
        pointHoverBorderColor: 'rgba(56, 189, 248, 0.8)',
        fill: true,
      }
    ],
    labels: [],
  };


  public lineChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    scales: {
      x: {
        title: {
          display: true,              // Must be true to show the axis name
          text: 'Date & Time',        // X-axis label
          color: '#fff',           // Slate-300 text color
          font: {
            size: 13,
            weight: 'bold'
          }
        },
        ticks: { color: '#fff' }, grid: { color: '#1e293b' }
      },
      y:
      {
        title: {
          display: true,              // Must be true to show the axis name
          text: 'Total Portfolio Value (₹)',         // Y-axis label
          color: '#fff',           // Slate-300 text color
          font: {
            size: 13,
            weight: 'bold'
          }
        },
        ticks: { color: '#fff' }, grid: { color: '#1e293b' }
      }
    },
    plugins: {
      legend: { labels: { color: '#f8fafc' } }
    }
  };

  loadPortfolioSnapshots(): void {
    const user: any = localStorage.getItem('user')
    this.portfolioService.getPortFolioSnapshotsByUserId(JSON.parse(user).id, 0, 20).subscribe(
      {
        next: (res: any) => {
          const reverseRes=[...res.content].reverse();
          // 1. Extract dates/timestamps for the X-axis (adjust property name to match your DTO)
          this.lineChartData.labels =reverseRes.map((item: any) =>
            new Date(item.recordedAt).toLocaleDateString('en-IN', {
              day: 'numeric',
              month: 'short',
              hour: '2-digit',
              minute: '2-digit',
              hour12: true // Shows "11 Nov, 06:55 pm" (set to false for 24-hour "18:55"
            })
          );

          // 2. Extract numeric portfolio values for the Y-axis
          this.lineChartData.datasets[0].data = res.content.map((item: any) => item.totalPortFolioValue);

          // 2. Extract numeric portfolio values for the Y-axis
          this.lineChartData.datasets[1].data = res.content.map((item: any) => item.cashBalance);

          // 3. Tell ng2-charts to re-render the canvas with the new data
          this.chart?.update();
        },
        error: (err) => {
          console.log(err)
        }
      })


  }



}
