/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ServiceChannelComponent } from 'app/entities/service-channel/service-channel.component';
import { ServiceChannelService } from 'app/entities/service-channel/service-channel.service';
import { ServiceChannel } from 'app/shared/model/service-channel.model';

describe('Component Tests', () => {
  describe('ServiceChannel Management Component', () => {
    let comp: ServiceChannelComponent;
    let fixture: ComponentFixture<ServiceChannelComponent>;
    let service: ServiceChannelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ServiceChannelComponent],
        providers: []
      })
        .overrideTemplate(ServiceChannelComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceChannelComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceChannelService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ServiceChannel(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.serviceChannels[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
