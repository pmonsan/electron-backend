import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConnectorType } from 'app/shared/model/connector-type.model';
import { ConnectorTypeService } from './connector-type.service';

@Component({
  selector: 'jhi-connector-type-delete-dialog',
  templateUrl: './connector-type-delete-dialog.component.html'
})
export class ConnectorTypeDeleteDialogComponent {
  connectorType: IConnectorType;

  constructor(
    protected connectorTypeService: ConnectorTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.connectorTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'connectorTypeListModification',
        content: 'Deleted an connectorType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-connector-type-delete-popup',
  template: ''
})
export class ConnectorTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ connectorType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ConnectorTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.connectorType = connectorType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/connector-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/connector-type', { outlets: { popup: null } }]);
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
