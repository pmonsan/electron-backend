import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceAuthentication } from 'app/shared/model/service-authentication.model';
import { ServiceAuthenticationService } from './service-authentication.service';

@Component({
  selector: 'jhi-service-authentication-delete-dialog',
  templateUrl: './service-authentication-delete-dialog.component.html'
})
export class ServiceAuthenticationDeleteDialogComponent {
  serviceAuthentication: IServiceAuthentication;

  constructor(
    protected serviceAuthenticationService: ServiceAuthenticationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceAuthenticationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceAuthenticationListModification',
        content: 'Deleted an serviceAuthentication'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-service-authentication-delete-popup',
  template: ''
})
export class ServiceAuthenticationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceAuthentication }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceAuthenticationDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.serviceAuthentication = serviceAuthentication;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/service-authentication', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/service-authentication', { outlets: { popup: null } }]);
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
