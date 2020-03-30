/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { Pg8583RequestDetailComponent } from 'app/entities/pg-8583-request/pg-8583-request-detail.component';
import { Pg8583Request } from 'app/shared/model/pg-8583-request.model';

describe('Component Tests', () => {
  describe('Pg8583Request Management Detail Component', () => {
    let comp: Pg8583RequestDetailComponent;
    let fixture: ComponentFixture<Pg8583RequestDetailComponent>;
    const route = ({ data: of({ pg8583Request: new Pg8583Request(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [Pg8583RequestDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(Pg8583RequestDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Pg8583RequestDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pg8583Request).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
