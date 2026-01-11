import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConsumerService } from '../services/consumer.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-bills',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './bills.component.html',
  styleUrl: './bills.component.css'
})
export class BillsComponent implements OnInit {
  bills: any;
  customerId!: number;

  constructor(
    private consumerService: ConsumerService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.customerId = this.route.snapshot.params['customerId'];
    if (this.customerId) {
      this.consumerService.getBillsByCustomerID(this.customerId).subscribe({
        next: (data) => {
          this.bills = data._embedded?.bills || [];
        },
        error: (err) => {
          console.error(err);
          this.bills = [];
        }
      });
    } else {
      this.consumerService.getAllBills().subscribe({
        next: (data) => {
          this.bills = data._embedded?.bills || [];
        },
        error: (err) => {
          console.error(err);
          this.bills = [];
        }
      });
    }
  }

  handleBillDetails(bill: any) {
    // Extract bill ID from _links.self.href or use bill.id
    let billId = bill.id;

    if (!billId && bill._links?.self?.href) {
      // Extract ID from href like "http://localhost:8083/api/bills/1"
      const href = bill._links.self.href;
      const parts = href.split('/');
      billId = parts[parts.length - 1];
    }

    if (billId) {
      this.router.navigate(['/bill-details', billId]);
    } else {
      console.error('Could not determine bill ID', bill);
    }
  }
}
