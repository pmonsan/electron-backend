import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConnector } from 'app/shared/model/connector.model';
import { ConnectorService } from './connector.service';

@Component({
  selector: 'jhi-connector-delete-dialog',
  templateUrl: './connector-delete-dialog.component.html'
})
export class ConnectorDeleteDialogComponent {
  connector: IConnector;

  constructor(protected connectorService: ConnectorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.connectorService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'connectorListModification',
        content: 'Deleted an connector'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-connector-delete-popup',
  template: ''
})
export class ConnectorDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ connector }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ConnectorDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.connector = connector;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/connector', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/connector', { outlets: { popup: null } }]);
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
