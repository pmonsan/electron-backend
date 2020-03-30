import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMeansofpaymentType } from 'app/shared/model/meansofpayment-type.model';
import { MeansofpaymentTypeService } from './meansofpayment-type.service';

@Component({
  selector: 'jhi-meansofpayment-type-delete-dialog',
  templateUrl: './meansofpayment-type-delete-dialog.component.html'
})
export class MeansofpaymentTypeDeleteDialogComponent {
  meansofpaymentType: IMeansofpaymentType;

  constructor(
    protected meansofpaymentTypeService: MeansofpaymentTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.meansofpaymentTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'meansofpaymentTypeListModification',
        content: 'Deleted an meansofpaymentType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-meansofpayment-type-delete-popup',
  template: ''
})
export class MeansofpaymentTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ meansofpaymentType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MeansofpaymentTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.meansofpaymentType = meansofpaymentType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/meansofpayment-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/meansofpayment-type', { outlets: { popup: null } }]);
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
