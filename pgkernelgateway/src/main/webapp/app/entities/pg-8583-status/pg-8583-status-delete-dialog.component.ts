import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPg8583Status } from 'app/shared/model/pg-8583-status.model';
import { Pg8583StatusService } from './pg-8583-status.service';

@Component({
  selector: 'jhi-pg-8583-status-delete-dialog',
  templateUrl: './pg-8583-status-delete-dialog.component.html'
})
export class Pg8583StatusDeleteDialogComponent {
  pg8583Status: IPg8583Status;

  constructor(
    protected pg8583StatusService: Pg8583StatusService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pg8583StatusService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pg8583StatusListModification',
        content: 'Deleted an pg8583Status'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pg-8583-status-delete-popup',
  template: ''
})
export class Pg8583StatusDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pg8583Status }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(Pg8583StatusDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pg8583Status = pg8583Status;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pg-8583-status', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pg-8583-status', { outlets: { popup: null } }]);
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
