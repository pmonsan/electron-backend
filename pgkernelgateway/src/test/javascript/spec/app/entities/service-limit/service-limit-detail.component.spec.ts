/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ServiceLimitDetailComponent } from 'app/entities/service-limit/service-limit-detail.component';
import { ServiceLimit } from 'app/shared/model/service-limit.model';

describe('Component Tests', () => {
  describe('ServiceLimit Management Detail Component', () => {
    let comp: ServiceLimitDetailComponent;
    let fixture: ComponentFixture<ServiceLimitDetailComponent>;
    const route = ({ data: of({ serviceLimit: new ServiceLimit(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ServiceLimitDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceLimitDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceLimitDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceLimit).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
