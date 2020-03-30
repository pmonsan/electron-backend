import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomerLimit } from 'app/shared/model/customer-limit.model';
import { CustomerLimitService } from './customer-limit.service';

@Component({
  selector: 'jhi-customer-limit-delete-dialog',
  templateUrl: './customer-limit-delete-dialog.component.html'
})
export class CustomerLimitDeleteDialogComponent {
  customerLimit: ICustomerLimit;

  constructor(
    protected customerLimitService: CustomerLimitService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.customerLimitService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'customerLimitListModification',
        content: 'Deleted an customerLimit'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-customer-limit-delete-popup',
  template: ''
})
export class CustomerLimitDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ customerLimit }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CustomerLimitDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.customerLimit = customerLimit;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/customer-limit', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/customer-limit', { outlets: { popup: null } }]);
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
