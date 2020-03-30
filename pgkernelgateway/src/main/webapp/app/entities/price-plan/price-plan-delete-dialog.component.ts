import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPricePlan } from 'app/shared/model/price-plan.model';
import { PricePlanService } from './price-plan.service';

@Component({
  selector: 'jhi-price-plan-delete-dialog',
  templateUrl: './price-plan-delete-dialog.component.html'
})
export class PricePlanDeleteDialogComponent {
  pricePlan: IPricePlan;

  constructor(protected pricePlanService: PricePlanService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pricePlanService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pricePlanListModification',
        content: 'Deleted an pricePlan'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-price-plan-delete-popup',
  template: ''
})
export class PricePlanDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pricePlan }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PricePlanDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pricePlan = pricePlan;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/price-plan', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/price-plan', { outlets: { popup: null } }]);
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
