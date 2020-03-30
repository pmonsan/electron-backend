import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInternalConnectorStatus } from 'app/shared/model/internal-connector-status.model';
import { InternalConnectorStatusService } from './internal-connector-status.service';

@Component({
  selector: 'jhi-internal-connector-status-delete-dialog',
  templateUrl: './internal-connector-status-delete-dialog.component.html'
})
export class InternalConnectorStatusDeleteDialogComponent {
  internalConnectorStatus: IInternalConnectorStatus;

  constructor(
    protected internalConnectorStatusService: InternalConnectorStatusService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.internalConnectorStatusService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'internalConnectorStatusListModification',
        content: 'Deleted an internalConnectorStatus'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-internal-connector-status-delete-popup',
  template: ''
})
export class InternalConnectorStatusDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ internalConnectorStatus }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(InternalConnectorStatusDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.internalConnectorStatus = internalConnectorStatus;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/internal-connector-status', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/internal-connector-status', { outlets: { popup: null } }]);
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
