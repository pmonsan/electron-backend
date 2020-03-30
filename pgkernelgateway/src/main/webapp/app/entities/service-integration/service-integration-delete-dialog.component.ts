import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceIntegration } from 'app/shared/model/service-integration.model';
import { ServiceIntegrationService } from './service-integration.service';

@Component({
  selector: 'jhi-service-integration-delete-dialog',
  templateUrl: './service-integration-delete-dialog.component.html'
})
export class ServiceIntegrationDeleteDialogComponent {
  serviceIntegration: IServiceIntegration;

  constructor(
    protected serviceIntegrationService: ServiceIntegrationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceIntegrationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceIntegrationListModification',
        content: 'Deleted an serviceIntegration'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-service-integration-delete-popup',
  template: ''
})
export class ServiceIntegrationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceIntegration }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceIntegrationDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.serviceIntegration = serviceIntegration;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/service-integration', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/service-integration', { outlets: { popup: null } }]);
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
