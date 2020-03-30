import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceChannel } from 'app/shared/model/service-channel.model';
import { ServiceChannelService } from './service-channel.service';

@Component({
  selector: 'jhi-service-channel-delete-dialog',
  templateUrl: './service-channel-delete-dialog.component.html'
})
export class ServiceChannelDeleteDialogComponent {
  serviceChannel: IServiceChannel;

  constructor(
    protected serviceChannelService: ServiceChannelService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceChannelService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceChannelListModification',
        content: 'Deleted an serviceChannel'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-service-channel-delete-popup',
  template: ''
})
export class ServiceChannelDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceChannel }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceChannelDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.serviceChannel = serviceChannel;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/service-channel', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/service-channel', { outlets: { popup: null } }]);
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
