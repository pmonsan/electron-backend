import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomerBlacklist } from 'app/shared/model/customer-blacklist.model';
import { CustomerBlacklistService } from './customer-blacklist.service';

@Component({
  selector: 'jhi-customer-blacklist-delete-dialog',
  templateUrl: './customer-blacklist-delete-dialog.component.html'
})
export class CustomerBlacklistDeleteDialogComponent {
  customerBlacklist: ICustomerBlacklist;

  constructor(
    protected customerBlacklistService: CustomerBlacklistService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.customerBlacklistService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'customerBlacklistListModification',
        content: 'Deleted an customerBlacklist'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-customer-blacklist-delete-popup',
  template: ''
})
export class CustomerBlacklistDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ customerBlacklist }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CustomerBlacklistDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.customerBlacklist = customerBlacklist;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/customer-blacklist', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/customer-blacklist', { outlets: { popup: null } }]);
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
