import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISubscriptionPrice } from 'app/shared/model/subscription-price.model';
import { SubscriptionPriceService } from './subscription-price.service';

@Component({
  selector: 'jhi-subscription-price-delete-dialog',
  templateUrl: './subscription-price-delete-dialog.component.html'
})
export class SubscriptionPriceDeleteDialogComponent {
  subscriptionPrice: ISubscriptionPrice;

  constructor(
    protected subscriptionPriceService: SubscriptionPriceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.subscriptionPriceService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'subscriptionPriceListModification',
        content: 'Deleted an subscriptionPrice'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-subscription-price-delete-popup',
  template: ''
})
export class SubscriptionPriceDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ subscriptionPrice }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SubscriptionPriceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.subscriptionPrice = subscriptionPrice;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/subscription-price', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/subscription-price', { outlets: { popup: null } }]);
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
