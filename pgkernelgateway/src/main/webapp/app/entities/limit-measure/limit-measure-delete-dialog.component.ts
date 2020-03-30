import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILimitMeasure } from 'app/shared/model/limit-measure.model';
import { LimitMeasureService } from './limit-measure.service';

@Component({
  selector: 'jhi-limit-measure-delete-dialog',
  templateUrl: './limit-measure-delete-dialog.component.html'
})
export class LimitMeasureDeleteDialogComponent {
  limitMeasure: ILimitMeasure;

  constructor(
    protected limitMeasureService: LimitMeasureService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.limitMeasureService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'limitMeasureListModification',
        content: 'Deleted an limitMeasure'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-limit-measure-delete-popup',
  template: ''
})
export class LimitMeasureDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ limitMeasure }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(LimitMeasureDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.limitMeasure = limitMeasure;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/limit-measure', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/limit-measure', { outlets: { popup: null } }]);
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
