import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceLimit } from 'app/shared/model/service-limit.model';
import { ServiceLimitService } from './service-limit.service';

@Component({
  selector: 'jhi-service-limit-delete-dialog',
  templateUrl: './service-limit-delete-dialog.component.html'
})
export class ServiceLimitDeleteDialogComponent {
  serviceLimit: IServiceLimit;

  constructor(
    protected serviceLimitService: ServiceLimitService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceLimitService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceLimitListModification',
        content: 'Deleted an serviceLimit'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-service-limit-delete-popup',
  template: ''
})
export class ServiceLimitDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceLimit }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceLimitDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.serviceLimit = serviceLimit;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/service-limit', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/service-limit', { outlets: { popup: null } }]);
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
