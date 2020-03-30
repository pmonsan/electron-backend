import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPrice } from 'app/shared/model/price.model';
import { PriceService } from './price.service';

@Component({
  selector: 'jhi-price-delete-dialog',
  templateUrl: './price-delete-dialog.component.html'
})
export class PriceDeleteDialogComponent {
  price: IPrice;

  constructor(protected priceService: PriceService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.priceService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'priceListModification',
        content: 'Deleted an price'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-price-delete-popup',
  template: ''
})
export class PriceDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ price }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PriceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.price = price;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/price', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/price', { outlets: { popup: null } }]);
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
