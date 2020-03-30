import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOperationType } from 'app/shared/model/operation-type.model';
import { OperationTypeService } from './operation-type.service';

@Component({
  selector: 'jhi-operation-type-delete-dialog',
  templateUrl: './operation-type-delete-dialog.component.html'
})
export class OperationTypeDeleteDialogComponent {
  operationType: IOperationType;

  constructor(
    protected operationTypeService: OperationTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.operationTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'operationTypeListModification',
        content: 'Deleted an operationType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-operation-type-delete-popup',
  template: ''
})
export class OperationTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ operationType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OperationTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.operationType = operationType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/operation-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/operation-type', { outlets: { popup: null } }]);
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
