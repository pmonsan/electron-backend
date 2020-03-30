import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomerSubscription } from 'app/shared/model/customer-subscription.model';
import { CustomerSubscriptionService } from './customer-subscription.service';

@Component({
  selector: 'jhi-customer-subscription-delete-dialog',
  templateUrl: './customer-subscription-delete-dialog.component.html'
})
export class CustomerSubscriptionDeleteDialogComponent {
  customerSubscription: ICustomerSubscription;

  constructor(
    protected customerSubscriptionService: CustomerSubscriptionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.customerSubscriptionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'customerSubscriptionListModification',
        content: 'Deleted an customerSubscription'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-customer-subscription-delete-popup',
  template: ''
})
export class CustomerSubscriptionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ customerSubscription }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CustomerSubscriptionDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.customerSubscription = customerSubscription;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/customer-subscription', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/customer-subscription', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
