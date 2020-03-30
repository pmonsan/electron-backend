import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOperationStatus } from 'app/shared/model/operation-status.model';
import { OperationStatusService } from './operation-status.service';

@Component({
  selector: 'jhi-operation-status-delete-dialog',
  templateUrl: './operation-status-delete-dialog.component.html'
})
export class OperationStatusDeleteDialogComponent {
  operationStatus: IOperationStatus;

  constructor(
    protected operationStatusService: OperationStatusService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.operationStatusService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'operationStatusListModification',
        content: 'Deleted an operationStatus'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-operation-status-delete-popup',
  template: ''
})
export class OperationStatusDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ operationStatus }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OperationStatusDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.operationStatus = operationStatus;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/operation-status', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/operation-status', { outlets: { popup: null } }]);
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
