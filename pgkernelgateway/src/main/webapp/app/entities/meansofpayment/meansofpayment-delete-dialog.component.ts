import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMeansofpayment } from 'app/shared/model/meansofpayment.model';
import { MeansofpaymentService } from './meansofpayment.service';

@Component({
  selector: 'jhi-meansofpayment-delete-dialog',
  templateUrl: './meansofpayment-delete-dialog.component.html'
})
export class MeansofpaymentDeleteDialogComponent {
  meansofpayment: IMeansofpayment;

  constructor(
    protected meansofpaymentService: MeansofpaymentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.meansofpaymentService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'meansofpaymentListModification',
        content: 'Deleted an meansofpayment'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-meansofpayment-delete-popup',
  template: ''
})
export class MeansofpaymentDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ meansofpayment }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MeansofpaymentDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.meansofpayment = meansofpayment;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/meansofpayment', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/meansofpayment', { outlets: { popup: null } }]);
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
