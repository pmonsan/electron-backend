import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInternalConnectorRequest } from 'app/shared/model/internal-connector-request.model';
import { InternalConnectorRequestService } from './internal-connector-request.service';

@Component({
  selector: 'jhi-internal-connector-request-delete-dialog',
  templateUrl: './internal-connector-request-delete-dialog.component.html'
})
export class InternalConnectorRequestDeleteDialogComponent {
  internalConnectorRequest: IInternalConnectorRequest;

  constructor(
    protected internalConnectorRequestService: InternalConnectorRequestService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.internalConnectorRequestService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'internalConnectorRequestListModification',
        content: 'Deleted an internalConnectorRequest'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-internal-connector-request-delete-popup',
  template: ''
})
export class InternalConnectorRequestDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ internalConnectorRequest }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(InternalConnectorRequestDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.internalConnectorRequest = internalConnectorRequest;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/internal-connector-request', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/internal-connector-request', { outlets: { popup: null } }]);
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
