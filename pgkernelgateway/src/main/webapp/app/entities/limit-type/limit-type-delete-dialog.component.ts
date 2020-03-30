import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILimitType } from 'app/shared/model/limit-type.model';
import { LimitTypeService } from './limit-type.service';

@Component({
  selector: 'jhi-limit-type-delete-dialog',
  templateUrl: './limit-type-delete-dialog.component.html'
})
export class LimitTypeDeleteDialogComponent {
  limitType: ILimitType;

  constructor(protected limitTypeService: LimitTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.limitTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'limitTypeListModification',
        content: 'Deleted an limitType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-limit-type-delete-popup',
  template: ''
})
export class LimitTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ limitType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(LimitTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.limitType = limitType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/limit-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/limit-type', { outlets: { popup: null } }]);
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
