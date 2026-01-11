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

  getBillId(bill: any): string {
    if (bill.id) return bill.id;
    if (bill._links?.self?.href) {
      const href = bill._links.self.href;
      const parts = href.split('/');
      return parts[parts.length - 1];
    }
    return 'N/A';
  }

  handleBillDetails(bill: any) {
    const billId = this.getBillId(bill);
    if (billId && billId !== 'N/A') {
      this.router.navigate(['/bill-details', billId]);
    } else {
      console.error('Could not determine bill ID', bill);
    }
  }
}
