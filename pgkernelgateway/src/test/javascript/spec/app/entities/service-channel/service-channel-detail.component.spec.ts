/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ServiceChannelDetailComponent } from 'app/entities/service-channel/service-channel-detail.component';
import { ServiceChannel } from 'app/shared/model/service-channel.model';

describe('Component Tests', () => {
  describe('ServiceChannel Management Detail Component', () => {
    let comp: ServiceChannelDetailComponent;
    let fixture: ComponentFixture<ServiceChannelDetailComponent>;
    const route = ({ data: of({ serviceChannel: new ServiceChannel(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ServiceChannelDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceChannelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceChannelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceChannel).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
