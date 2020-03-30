import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPg8583Callback } from 'app/shared/model/pg-8583-callback.model';
import { Pg8583CallbackService } from './pg-8583-callback.service';

@Component({
  selector: 'jhi-pg-8583-callback-delete-dialog',
  templateUrl: './pg-8583-callback-delete-dialog.component.html'
})
export class Pg8583CallbackDeleteDialogComponent {
  pg8583Callback: IPg8583Callback;

  constructor(
    protected pg8583CallbackService: Pg8583CallbackService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pg8583CallbackService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pg8583CallbackListModification',
        content: 'Deleted an pg8583Callback'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pg-8583-callback-delete-popup',
  template: ''
})
export class Pg8583CallbackDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pg8583Callback }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(Pg8583CallbackDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pg8583Callback = pg8583Callback;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pg-8583-callback', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pg-8583-callback', { outlets: { popup: null } }]);
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
