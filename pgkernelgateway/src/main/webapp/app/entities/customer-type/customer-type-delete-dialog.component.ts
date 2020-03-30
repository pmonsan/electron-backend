import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomerType } from 'app/shared/model/customer-type.model';
import { CustomerTypeService } from './customer-type.service';

@Component({
  selector: 'jhi-customer-type-delete-dialog',
  templateUrl: './customer-type-delete-dialog.component.html'
})
export class CustomerTypeDeleteDialogComponent {
  customerType: ICustomerType;

  constructor(
    protected customerTypeService: CustomerTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.customerTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'customerTypeListModification',
        content: 'Deleted an customerType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-customer-type-delete-popup',
  template: ''
})
export class CustomerTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ customerType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CustomerTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.customerType = customerType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/customer-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/customer-type', { outlets: { popup: null } }]);
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
