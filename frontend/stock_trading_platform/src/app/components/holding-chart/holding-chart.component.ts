import { Component, inject, ViewChild } from '@angular/core';
import { ChartConfiguration } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { HoldingService } from '../../services/holding.service';

@Component({
  selector: 'app-holding-chart',
  standalone: true,
  imports: [],
  templateUrl: './holding-chart.component.html',
  styleUrl: './holding-chart.component.css'
})
export class HoldingChartComponent {
  @ViewChild(BaseChartDirective) chart: BaseChartDirective | undefined;
  holdingService=inject(HoldingService)
  user:any=JSON.parse(localStorage.getItem('user') as any)

    // Use 'pie' as const (or 'doughnut' as const)
  public pieChartType = 'pie' as const;

  public pieChartData: ChartConfiguration<'pie'>['data'] = {
    labels: ['Desktop', 'Mobile', 'Tablet'],
    datasets: [
      {
        data:[0],
        backgroundColor: ['#3f51b5', '#ff4081', '#009688'],
        hoverBackgroundColor: ['#303f9f', '#f50057', '#00796b'],
        borderColor: '#ffffff',
        borderWidth: 2,
        // Pulls out the first slice ('Desktop') by 20px
        //offset:,
        // Hover pop-out effect
        hoverOffset: 10
      }
    ]
  };

  public pieChartOptions: ChartConfiguration<'pie'>['options'] = {
    responsive: true,
    // Set cutout: '50%' to turn this pie into a doughnut
    cutout: '0%',
    // Uncomment both lines below for a Half-Pie / Gauge look:
    // rotation: -90,
    // circumference: 180,
    plugins: {
      legend: {
        position: 'top'
      }
    }
  }


}
