import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPriceCommission } from 'app/shared/model/price-commission.model';
import { PriceCommissionService } from './price-commission.service';

@Component({
  selector: 'jhi-price-commission-delete-dialog',
  templateUrl: './price-commission-delete-dialog.component.html'
})
export class PriceCommissionDeleteDialogComponent {
  priceCommission: IPriceCommission;

  constructor(
    protected priceCommissionService: PriceCommissionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.priceCommissionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'priceCommissionListModification',
        content: 'Deleted an priceCommission'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-price-commission-delete-popup',
  template: ''
})
export class PriceCommissionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ priceCommission }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PriceCommissionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.priceCommission = priceCommission;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/price-commission', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/price-commission', { outlets: { popup: null } }]);
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
